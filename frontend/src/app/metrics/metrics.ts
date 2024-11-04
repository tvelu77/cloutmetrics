import { LanguagesRatio } from "./languages-ratio";

export interface Metrics {
    id: number;
    totalCommits: number;
    languageAndFileCount: LanguagesRatio;
}
