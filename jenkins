pipeline {
    agent any
    
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
       
        stage('Generate HTML report') {
           steps { 
              cucumber buildStatus: 'UNSTABLE',
                reportTitle: 'My report',
                fileIncludePattern: '**/*.json',
                trendsLimit: 10,
                classifications: [
                    [
                        'key': 'Browser',
                        'value': 'Firefox'
                    ]
                ]
           }
       }
        
       
    }
   
     post {
            always {
                cucumber '**/cucumber.json'
            }
         }   
}
