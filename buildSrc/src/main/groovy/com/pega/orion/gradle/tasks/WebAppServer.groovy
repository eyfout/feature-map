package com.pega.orion.gradle.tasks

import com.pega.orion.gradle.tasks.docker.WebAppConfiguration
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class WebAppServer extends DefaultTask implements WebAppConfiguration {
    ApplicationServer webApp
    String webAppVersion

    @TaskAction
    void create() {
        this.
        Task dockerFile = project.tasks.create (['type': webApp.impl(), 'constructorArgs':this])
    }

    @Override
    @Input
    ApplicationServer getWebApp() {
        return webApp
    }

    @Override
    @Input
    String getWebAppVersion() {
        return webAppVersion
    }
}
