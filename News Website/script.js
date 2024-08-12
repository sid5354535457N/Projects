const Api_key="f2757264c7474175915647b179ce6c6a";
const url="https://newsapi.org/v2/everything?q=";

window.addEventListener('load',()=>fetchNews("India"));

function reload() {
    window.location.reload();  
}

async function fetchNews (query) {
    const resp=await fetch(`${url}${query}&apiKey=${Api_key}`);
    const data=await resp.json();
    bindData(data.articles);
}

function bindData(articles) {
    const cardsContainer=document.getElementById('cards-container');
    const newsCardTemp=document.getElementById('template-news');
    cardsContainer.innerHTML='';

    articles.forEach(article => {
        if(!article.urlToImage) {
            return;
        }
        const cardClone=newsCardTemp.content.cloneNode(true);
        fillDataInCard(cardClone,article);
        cardsContainer.appendChild(cardClone);
    });
}

function fillDataInCard(cardClone,article) {
    const newsImg=cardClone.querySelector('#newsimg');
    const newsTitle=cardClone.querySelector('#news-title');
    const newsSource=cardClone.querySelector('#news-source');
    const newsDesc=cardClone.querySelector('#newsdesc');

    newsImg.src=article.urlToImage;
    newsTitle.innerHTML=article.title;
    newsDesc.innerHTML=article.description;

    const date=new Date(article.publishedAt).toLocaleString("en-US",{
        timeZone:"Asia/Jakarta"
    });
    newsSource.innerHTML=`${article.source.name} . ${date}`;

    cardClone.firstElementChild.addEventListener('click',()=>{
        window.open(article.url,"_blank");
    })
}

let currNav=null;
function onNavItemClick(id){
    fetchNews(id);
    const navItem=document.getElementById(id);
    currNav?.classList.remove('active');
    currNav=navItem;
    currNav.classList.add('active');
}

const searchbt=document.getElementById('searchbtn');
const searchtxt=document.getElementById('search-text');

searchbt.addEventListener('click',()=> {
    const query=searchtxt.value;
    if(!query) return;
    fetchNews(query);
    currNav?.classList.remove('active');
    currNav=null;
})