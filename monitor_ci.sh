#!/bin/bash
COMMIT_SHA="82d534b4bf21246a2746edb2798be11d29e7522c"
REPO="ganesh47/java-99-problems"

while true; do
  RUNS=$(gh run list --repo "$REPO" --commit "$COMMIT_SHA" --json status,conclusion,workflowName,url)
  
  # Check if all runs are completed and successful
  ALL_COMPLETED=true
  ALL_SUCCESS=true
  
  # Use jq to iterate over the runs
  while read -r run; do
    status=$(echo "$run" | jq -r '.status')
    conclusion=$(echo "$run" | jq -r '.conclusion')
    
    if [ "$status" != "completed" ]; then
      ALL_COMPLETED=false
    fi
    if [ "$conclusion" != "success" ]; then
      ALL_SUCCESS=false
    fi
  done <<< "$(echo "$RUNS" | jq -c '.[]')"
  
  if $ALL_COMPLETED; then
    if $ALL_SUCCESS; then
      echo "All checks passed for commit $COMMIT_SHA."
      echo "$RUNS" | jq -C '.'
      exit 0
    else
      echo "Some checks failed or were cancelled for commit $COMMIT_SHA."
      echo "$RUNS" | jq -C '.'
      exit 1
    fi
  fi
  
  echo "Checks are still in progress for commit $COMMIT_SHA. Waiting 30 seconds..."
  sleep 30
done
