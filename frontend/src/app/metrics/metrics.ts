import { LanguagesRatio } from "./languages-ratio";

export interface Metrics {
    id: number;
    totalCommits: number;
    totalTags: number;
    totalBranches: string;
    owner: string;
    languageAndFileCount: LanguagesRatio;
    languagesRatio: LanguagesRatio;
}
