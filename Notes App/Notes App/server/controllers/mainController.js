exports.homepage=async(req,res)=>{
    const locals={
        title:"Notes Hub",
        description:"Enjoy the freedom of making notes",
    };
    res.render("index",{
        locals,
        layout:"../views/layouts/front-page"
    });
}


exports.about=async(req,res)=>{
    const locals={
        title:"About-Notes Hub",
        description:"Enjoy the freedom of making notes",
    };
    res.render("about",locals);
}