
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
            absUrl:getContextPath() + "/site/index.html#/login",
        },
        forbidden:{
            url:"/forbidden"
        }
    },
    security:{
        defaultStatus:401,
        acl:[
            ['user','routeInstance',function(user,routeInstance){
                if (user) {
                    if (user.tipoUsuario==="TITULADO") {
                        return 200;
                    } else {
                        //Si no es un titulado prohibimos el acceso
                        return 403;
                    }
                } else {
                    //Si no hay usuario, tiene que autenticarse
                    return 401;
                }
            }]
        ]
    }
});