#!groovy

import jenkins.model.Jenkins
import jenkins.metrics.api.*

def metricApiKey = new File("/run/secrets/metric-key").text.trim()
def metrics = Jenkins.instance.getExtensionList(MetricsAccessKey.DescriptorImpl)[0]

def metricsAccessKeys = [
        // String description, String key, boolean canPing, boolean canThreadDump, boolean canHealthCheck, boolean canMetrics, String origin
        new MetricsAccessKey("Jenkins Metric API", metricApiKey, true, true, true, true, "*")
]

metrics.setAccessKeys(metricsAccessKeys)