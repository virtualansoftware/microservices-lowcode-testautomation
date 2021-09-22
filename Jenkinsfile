pipeline {
    agent any
    parameters {
        choice(
          name: 'IDAITHALAM_EXECUTION_ENV',
          choices: ['Dev', 'test', 'SIT', 'UAT'],
          description: 'Select Environment to Test')
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

        stage ('Compile Stage') {
            steps {
                  bat 'mvn clean compile'
            }
        }
        stage ('Testing Stage') {
            steps {

                    bat 'mvn test'
            }
        }


    }

    post {
        always {
            cucumber '**/cucumber-*.json'

        }
    }

}