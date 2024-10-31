import { LanguagesRatio } from "./languages-ratio";

export interface Metrics {
    id: number;
    totalCommits: number;
    languagesRatio: LanguagesRatio;
}
