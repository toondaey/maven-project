pipeline {
	agent any

	tools {
        maven 'localMavin'
    }

	stages {
		stage ("Init") {
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

		stage("Deploy to Staging") {
			steps {
				build job: "deploy-to-staging"
			}
		}

		stage ("Deploy to Production") {
			steps {
				timeout(time: 5, unit:"DAYS") {
					input message:"Approve for production?"
				}
				build job: "deploy-to-prod"
			}
			post {
				success {
					echo "Deployment successful."
				}
				failure {
					echo "Deployment failed."
				}
			}
		}
	}	
}