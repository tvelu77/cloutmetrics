import { Metrics } from "../metrics/metrics";

export interface Git {
    id?: number;
    name?: string;
    url: string;
    date?: Date;
    status?: GitStatus;
    metrics?: Metrics;
}

export enum GitStatus {
    UPLOADED = "The URL has been uploaded",
    IN_PROGESS = "Metrics are being computed, please wait",
    ERROR = "Metrics couldn't be computed",
    FINISHED = "Metrics computed",
}
