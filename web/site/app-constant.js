app.constant("ix3UserConfig",{
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
        login:getContextPath() + "/site/index.html#/login",
        forbidden:"#/forbidden",
        error:"#/forbidden"
    }
});
