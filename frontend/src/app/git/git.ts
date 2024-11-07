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
    UPLOADED = "UPLOADED",
    IN_PROGESS = "IN_PROGRESS",
    ERROR = "ERROR",
    FINISHED = "FINISHED",
}
