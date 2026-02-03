const accesskey="tg3X-z7vVeIVa4TZ3YXX9CBIUDVO-AbDozO8S7bJ6qk";
const searchform=document.querySelector("#search-form");
const searchbox=document.querySelector("#search-box");
const searchResult=document.querySelector("#search-result");
const searcMbtn=document.querySelector("#show-more");
let keyword="";
let page=1;
function reload() {
    window.location.reload();  
}
async function searchImages() {
    keyword=searchbox.value;
    const url=`https://api.unsplash.com/search/photos?page=${page}&query=${keyword}&client_id=${accesskey}&per_page=12`;

    const response=await fetch(url);
    const data=await response.json();
    if(page===1) {
        searchResult.innerHTML="";
        
    }
    const results=data.results;
    results.map((result)=> {
        const image=document.createElement("img");
        image.src=result.urls.small;
        const imglink=document.createElement("a");
        imglink.href=result.links.html;
        imglink.target="_blank";

        imglink.appendChild(image);

        searchResult.appendChild(imglink);

    })
    searcMbtn.style.display="block";
}
searchform.addEventListener("submit",(e)=> {
    e.preventDefault();
    page=1;
    searchImages();
});
searcMbtn.addEventListener("click",()=> {
    page++;
    searchImages();
})