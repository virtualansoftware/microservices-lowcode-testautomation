pipeline {
    agent any
    parameters {
        choice(
                name: 'Execute Tests',
                choices: ['Dev', 'SIT', 'UAT'],
                description: 'Select Environment to deploy')
    }
    environment {
        IDAITHALAM = 'PROD'
        EMAIL_ID = "elan.thangamani@virtualan.io"
        IDAITHALAM_EXECUTION_ENV = 'test'

    }
    stages {

        stage('Execute Test') {

            steps {

                withMaven(jdk: 'JAVA_8', maven: 'Maven 3.3.9') {

                    sh "mvn clean test"

                }


            }
        }


    }

    post {
        always {
            cucumber '**/cucumber-*.json'

        }
    }

}