pipeline {
	agent any

	tools {
        maven 'localMavin'
    }

	stages {
		stage("Init") {
			steps {
				sh 'mvn clean package'
			}
			post {
				success {
					echo 'Archiving artifacts...'
					archiveArtifacts artifacts: "**/target/*.war"
				}
			}
		}
	}	
}