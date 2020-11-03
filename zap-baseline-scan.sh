docker run -v $(pwd):/zap/wrk/:rw -t owasp/zap2docker-stable zap-baseline.py -t http://192.168.178.31:8080 -r zap_baseline_report.html
