pipeline {
	agent any

	tools {
        maven 'localMavin'
    }

    parameters {
    	string (name: "tomcat_staging", defaultValue: "13.59.243.42", description: "Staging server")
    	string (name: "tomcat_prod", defaultValue: "18.218.236.204", description: "Production server")
    	string (name: "artifactLocale", defaultValue: "**/target/*.war", description: "Location of artifacts.")
    }

    triggers {
    	pollSCM ("* * * * *")
    }

	stages {
		stage ("Build") {
			steps {
				sh 'mvn clean package'
			}
			post {
				success {
					echo 'Archiving artifacts...'
					archiveArtifacts artifacts: "${params.artifactLocale}"
				}
			}
		}

		stage ("Deployment") {
			parallel {
				stage ("Staging") {
					steps {
						sh "scp -i '/Users/toonday/Tunde/Personal/jenkins-demo.pem' ${params.artifactLocale} ssh ec2-user@${params.tomcat_staging}:/var/lib/tomcat7/webapps"
					}
					post {
						success {
							echo "Deployment to staging completed."
						}
					}
				}
				stage ("Production") {
					steps {
						timeout (time: 5, unit: "DAYS") {
							input message: "Deploy to production?"
						}
						sh "scp -i '/Users/toonday/Tunde/Personal/jenkins-demo.pem' ${params.artifactLocale} ssh ec2-user@${params.tomcat_prod}:/var/lib/tomcat7/webapps"
					}
					post {
						success {
							echo "Deployment to production completed."
						}
						failure {
							echo "Something went wrong."
						}
					}
				}
			}
		}
	}

	// 	stage("Deploy to Staging") {
	// 		steps {
	// 			build job: "deploy-to-staging"
	// 		}
	// 	}

	// 	stage ("Deploy to Production") {
	// 		steps {
	// 			timeout(time: 5, unit:"DAYS") {
	// 				input message:"Approve for production?"
	// 			}
	// 			build job: "deploy-to-prod"
	// 		}
	// 		post {
	// 			success {
	// 				echo "Deployment successful."
	// 			}
	// 			failure {
	// 				echo "Deployment failed."
	// 			}
	// 		}
	// 	}
	// }	
}