pipeline {
	agent any

	tools {
        maven 'localMavin'
    }

    parameters {
    	string (name: "tomcat_staging", value: "ec2-13-59-243-42.us-east-2.compute.amazonaws.com", description: "Staging server")
    	string (name: "tomcat_prod", value: "ec2-18-218-236-204.us-east-2.compute.amazonaws.com", description: "Production server")
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
					archiveArtifacts artifacts: "**/target/*.war"
				}
			}
		}

		stage ("Deployment") {
			parallel {
				stage("Staging") {
					steps {
						sh "scp -i '~/Downloads/jenkins-demo.pem' **/target/*.war ssh ec2-user@${params.tomcat_staging}:/var/lib/tomcat7/webapp"
					}
					post {
						success {
							echo "Deployment to staging completed."
						}
					}
				}
				stage("Production") {
					timeout (time: 5, unit: "DAYS") {
						input message: "Deploy to production?"
					}
					steps {
						sh "scp -i '~/Downloads/jenkins-demo.pem' **/target/*.war ssh ec2-user@${params.tomcat_prod}:/var/lib/tomcat7/webapp"
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