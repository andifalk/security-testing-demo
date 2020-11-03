docker run -v $(pwd):/zap/wrk/:rw -t owasp/zap2docker-weekly zap-api-scan.py \
-t http://192.168.178.31:8080/v3/api-docs -f openapi \
-z "-configfile /zap/wrk/options.prop" \
-r zap_api_report.html -x report_xml.xml
