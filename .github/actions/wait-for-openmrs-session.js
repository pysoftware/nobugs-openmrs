- name: Wait for OpenMRS backend
  run: |
    echo "Waiting for OpenMRS backend to be ready..."

    for i in {1..600}; do
      STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/openmrs/ws/rest/v1/session || true)

      echo "Attempt $i - HTTP status: $STATUS"

      if [ "$STATUS" = "200" ]; then
        echo "✅ OpenMRS backend is ready"
        exit 0
      fi

      sleep 60
    done

    echo "❌ OpenMRS failed to start in time"
    exit 1