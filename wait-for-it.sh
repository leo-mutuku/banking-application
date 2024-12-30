#!/usr/bin/env bash
# wait-for-it.sh

# This script waits until a specific host and port are available.
# Example usage: wait-for-it.sh postgres:5432 -- java -jar app.jar

TIMEOUT=30
QUIET=0

# Parse the arguments.
while [[ $# -gt 0 ]]; do
    case $1 in
        --timeout)
            TIMEOUT=$2
            shift 2
            ;;
        --quiet)
            QUIET=1
            shift
            ;;
        *)
            HOST=$1
            PORT=$2
            shift 2
            ;;
    esac
done

# Start the timer.
START=$(date +%s)

# Loop to check if the host is available.
while ! nc -z "$HOST" "$PORT"; do
    # Check if we've reached the timeout.
    ELAPSED=$(( $(date +%s) - $START ))
    if [[ $ELAPSED -gt $TIMEOUT ]]; then
        echo "Timeout reached: $HOST:$PORT is not available."
        exit 1
    fi

    # Print progress (only if not quiet).
    if [[ $QUIET -eq 0 ]]; then
        echo "Waiting for $HOST:$PORT to become available..."
    fi

    sleep 1
done

# If we've reached this point, the service is available.
echo "$HOST:$PORT is available."
#!/usr/bin/env bash
# wait-for-it.sh

# This script waits until a specific host and port are available.
# Example usage: wait-for-it.sh postgres:5432 -- java -jar app.jar

TIMEOUT=30
QUIET=0

# Parse the arguments.
while [[ $# -gt 0 ]]; do
    case $1 in
        --timeout)
            TIMEOUT=$2
            shift 2
            ;;
        --quiet)
            QUIET=1
            shift
            ;;
        *)
            HOST=$1
            PORT=$2
            shift 2
            ;;
    esac
done

# Start the timer.
START=$(date +%s)

# Loop to check if the host is available.
while ! nc -z "$HOST" "$PORT"; do
    # Check if we've reached the timeout.
    ELAPSED=$(( $(date +%s) - $START ))
    if [[ $ELAPSED -gt $TIMEOUT ]]; then
        echo "Timeout reached: $HOST:$PORT is not available."
        exit 1
    fi

    # Print progress (only if not quiet).
    if [[ $QUIET -eq 0 ]]; then
        echo "Waiting for $HOST:$PORT to become available..."
    fi

    sleep 1
done

# If we've reached this point, the service is available.
echo "$HOST:$PORT is available."
#!/usr/bin/env bash
# wait-for-it.sh

# This script waits until a specific host and port are available.
# Example usage: wait-for-it.sh postgres:5432 -- java -jar app.jar

TIMEOUT=30
QUIET=0

# Parse the arguments.
while [[ $# -gt 0 ]]; do
    case $1 in
        --timeout)
            TIMEOUT=$2
            shift 2
            ;;
        --quiet)
            QUIET=1
            shift
            ;;
        *)
            HOST=$1
            PORT=$2
            shift 2
            ;;
    esac
done

# Start the timer.
START=$(date +%s)

# Loop to check if the host is available.
while ! nc -z "$HOST" "$PORT"; do
    # Check if we've reached the timeout.
    ELAPSED=$(( $(date +%s) - $START ))
    if [[ $ELAPSED -gt $TIMEOUT ]]; then
        echo "Timeout reached: $HOST:$PORT is not available."
        exit 1
    fi

    # Print progress (only if not quiet).
    if [[ $QUIET -eq 0 ]]; then
        echo "Waiting for $HOST:$PORT to become available..."
    fi

    sleep 1
done

# If we've reached this point, the service is available.
echo "$HOST:$PORT is available."
