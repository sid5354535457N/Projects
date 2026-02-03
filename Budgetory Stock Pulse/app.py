import streamlit as st
import yfinance as yf
import google.generativeai as genai
import feedparser
import os
from datetime import datetime
from dotenv import load_dotenv


load_dotenv()
genai.configure(api_key=os.getenv("GEMINI_API_KEY"))
model = genai.GenerativeModel('gemini-2.5-flash')

st.set_page_config(page_title="Budget AI Analyzer", layout="wide")
st.title("📈 Budget-to-Stock AI Analyzer")


ticker_input = st.sidebar.text_input("Enter NSE Stock Ticker", "RVNL")
ticker = ticker_input.upper() + ".NS" if not ticker_input.endswith(".NS") else ticker_input.upper()

if st.sidebar.button("Run Analysis"):
    try:
        
        now = datetime.now()
        timestamp_str = now.strftime("%d %b %Y | %I:%M %p")

        stock = yf.Ticker(ticker)
        hist = stock.history(period="7d")
        current_price = hist['Close'].iloc[-1]
        
    
        col1, col2 = st.columns([2, 1])
        with col1:
            st.subheader(f"Stock: {ticker_input}")
            st.write(f"⏱️ **Retrieved on:** {timestamp_str}")
            st.metric(label="Current Price", value=f"₹{current_price:.2f}")
        
        
        st.write("### 📊 7-Day Price Trend")
        st.line_chart(hist['Close'])

        
        st.write("---")
        st.write("### 📰 AI Sentiment Analysis")
        feed = feedparser.parse("https://www.livemint.com/rss/budget")
        
        report_content = f"FINANCIAL ANALYSIS REPORT\nSTOCKED: {ticker_input}\nDATE: {timestamp_str}\nPRICE: ₹{current_price:.2f}\n"
        report_content += "-"*30 + "\n\n"

        for entry in feed.entries[:3]:
            headline = entry.title
            prompt = f"How does this news: '{headline}' affect {ticker_input}? Score -1 to 1 and give 1 sentence."
            response = model.generate_content(prompt)
            ai_analysis = response.text
            
            with st.expander(f"News: {headline}"):
                st.write(ai_analysis)
            
            
            report_content += f"HEADLINE: {headline}\nANALYSIS: {ai_analysis}\n\n"

       
        st.sidebar.write("---")
        st.sidebar.download_button(
            label="📩 Download Analysis Report",
            data=report_content,
            file_name=f"Report_{ticker_input}_{now.strftime('%Y%m%d')}.txt",
            mime="text/plain"
        )
                
    except Exception as e:
        st.error(f"Error: {e}")