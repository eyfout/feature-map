package com.pega.orion.gradle.tasks.docker

class Tomcat extends AbstractWebApp {
    def ROOT_DIR = new File('/usr/local/tomcat')

    @Override
    void create() {
        println 'Tomcat'
        super.create()
    }
}
