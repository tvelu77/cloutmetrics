import { Metrics } from "../metrics/metrics";

export interface Git {
    id?: number;
    name?: string;
    url: string;
    date?: Date;
    metrics?: Metrics;
}
