var reporter = require('cucumber-html-reporter');

var options = {
        theme: 'bootstrap',
        jsonDir: './target/LakesideMutual/Customer-Self-Service/1/.*/',
        output: './target/cucumber_parallel.html',
        reportSuiteAsScenarios: true,
        scenarioTimestamp: true,
        ignoreBadJsonFile: true,
        launchReport: true,
        metadata: {
            "App Version":"0.3.2",
            "Test Environment": "STAGING",
            "Browser": "Chrome  54.0.2840.98",
            "Platform": "Windows 10",
            "Parallel": "Scenarios",
            "Executed": "Remote"
        }
    };

    reporter.generate(options);
    