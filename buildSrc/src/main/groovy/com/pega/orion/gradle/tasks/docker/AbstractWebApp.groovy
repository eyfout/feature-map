package com.pega.orion.gradle.tasks.docker

import com.bmuschko.gradle.docker.tasks.image.Dockerfile

import javax.inject.Inject

class AbstractWebApp extends Dockerfile implements WebApp {
    protected WebAppConfiguration configuration
    def PEGA_DIR = new File('/opt/pega')

    @Inject
    void create(WebAppConfiguration configuration) {
        this.configuration = configuration
        from("$configuration.getWebApp():$configuration.getWebAppVersion()")
        maintainer('Sidney.Milien@pega.com')
        create()
    }
}
