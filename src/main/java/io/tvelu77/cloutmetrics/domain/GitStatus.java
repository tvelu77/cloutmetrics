package io.tvelu77.cloutmetrics.domain;

/**
 * Defines the status of an uploaded {@link Git}.<br>
 * It can be :<br>
 * - UPLOADED, the git has been created ;<br>
 * - IN_PROGRESS, the {@link Metrics} are being computed ;<br>
 * - ERROR, the {@link Metrics} could not be computed ;<br>
 * - FINISHED, the {@link Metrics} has been successfully computed.
 */
public enum GitStatus {

  UPLOADED,
  IN_PROGRESS,
  ERROR,
  FINISHED

}
