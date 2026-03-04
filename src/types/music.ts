export interface MusicItem {
  chart_id: number;
  level_id: number;
  level_String: string;
  score: number;
  music_name: string;
  dx_score: string;
  combo_type: number;
  sync_type: number;
  score_type: number;
  type: number;
  genre?: string;
  playCount? : number;
  lastPlayDate? : string;
}

export interface MusicStatus {
  clearCount: number;
  sCount: number;
  spCount: number;
  ssCount: number;
  sspCount: number;
  sssCount: number;
  ssspCount: number;
  fcCount: number;
  fcpCount: number;
  apCount: number;
  appCount: number;
  syncCount: number;
  fsCount: number;
  fspCount: number;
  fdxCount: number;
  fdxpCount: number;
  dxStar1Count: number;
  dxStar2Count: number;
  dxStar3Count: number;
  dxStar4Count: number;
  dxStar5Count: number;
  randDollyCount: number;
}

export interface MusicData {
  songs: { [key: string]: MusicItem[] };
  total: number;
  musicStatus: MusicStatus;
  genreOrder?: string[];
}

export interface Score {
  chart_id: number;
  img_url: string;
  type: string;
  title: string;
  artist: string;
  ratingItems: MusicItem[];
}
