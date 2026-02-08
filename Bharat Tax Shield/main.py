import os
import io
import pandas as pd
from fastapi import FastAPI, Request, File, UploadFile
from fastapi.templating import Jinja2Templates
from fastapi.responses import HTMLResponse
from dotenv import load_dotenv
from google import genai
from engine import calculate_harvest
load_dotenv()

app = FastAPI()
templates = Jinja2Templates(directory="templates")

client = genai.Client(api_key=os.getenv("GEMINI_API_KEY"))

async def get_ai_summary(results):
    prompt = f"""
    As an Indian Tax Expert, explain how booking ₹{results['stcg']} in STCG loss 
    and ₹{results['ltcg']} in LTCG loss will save exactly ₹{results['tax_saved']} in tax.
    Use 3 bullet points. Explain that STCG saves 20% and LTCG saves 12.5%.
    Keep it professional and encouraging.
    """
    try:
        response = client.models.generate_content(
            model="gemini-2.5-flash", 
            contents=prompt
        )
        return response.text
    except Exception as e:
        return "AI Summary is currently updating. Your calculated savings are ready below."

@app.get("/", response_class=HTMLResponse)
async def intro_page(request: Request):
    return templates.TemplateResponse("index.html", {"request": request})

@app.post("/analyze", response_class=HTMLResponse)
async def analyze_portfolio(request: Request, file: UploadFile = File(...)):
    contents = await file.read()
    df = pd.read_csv(io.BytesIO(contents))
    results = calculate_harvest(df)
    ai_summary = await get_ai_summary(results)
    
    return templates.TemplateResponse("result.html", {
        "request": request,
        "stcg": results['stcg'],
        "ltcg": results['ltcg'],
        "tax_saved": results['tax_saved'],
        "stocks": results['stocks'],
        "summary": ai_summary
    })