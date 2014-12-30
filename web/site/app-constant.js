app.constant("ix3UserConfiguration",{
    bootstrap: {
        version:3
    },
    server: {
        api:getContextPath() + "/api"
    },
    format: {
        date: {
            default:"dd/MM/yyyy"
        }
    },
    pages: {
        login:{
            url:"/login",
        },
        forbidden:{
            url:"/forbidden"
        }
    },
    security:{
        defaultStatus:200,
        acl:[
            
        ]
    }
});
