import pandas as pd
from datetime import datetime

def calculate_harvest(df):
   
    df['buy_date'] = pd.to_datetime(df['buy_date'])
    df['days_held'] = (datetime.now() - df['buy_date']).dt.days
    
   
    df['pnl'] = (df['current_price'] - df['buy_price']) * df['quantity']
   
    losses = df[df['pnl'] < 0].copy()
    
   
    stcg_loss = abs(losses[losses['days_held'] <= 365]['pnl'].sum())
    ltcg_loss = abs(losses[losses['days_held'] > 365]['pnl'].sum())
    
    # NEW: Exact Tax Savings Calculation (2026 Rules)
    stcg_tax_saved = stcg_loss * 0.20
    ltcg_tax_saved = ltcg_loss * 0.125
    total_savings = stcg_tax_saved + ltcg_tax_saved
    
    
    recommendations = []
    for _, row in losses.iterrows():
        recommendations.append({
            "symbol": row['symbol'], 
            "pnl": round(row['pnl'], 2),
            "type": "STCG" if row['days_held'] <= 365 else "LTCG"
        })
    
    return {
        "stcg": round(stcg_loss, 2),
        "ltcg": round(ltcg_loss, 2),
        "tax_saved": round(total_savings, 2),
        "stocks": recommendations
    }