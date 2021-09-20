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


    tools {
        maven "Maven"
    }

    stages {

        stage('Execute Test') {

            steps {

                    sh "mvn clean test"

            }
        }


    }

    post {
        always {
            cucumber '**/cucumber-*.json'

        }
    }

}