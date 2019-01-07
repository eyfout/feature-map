package com.pega.orion.gradle.tasks.docker

interface WebAppConfiguration {
    enum ApplicationServer {
        TOMCAT(Tomcat.class)
        Class<WebApp> aClass

        ApplicationServer(Class aClass) {
            this.aClass = aClass
        }

        Class impl(){
            return aClass
        }

    }

    ApplicationServer getWebApp()
    String getWebAppVersion()

}
