export interface PlaylogRecord {
  recordId: string;
  trackNumber: string;
  playDate: string;
  levelId: number;
  musicName: string;
  musicImage: string;
  isDx: boolean;
  isNewRecord: boolean;
  achievement: string;
  scoreType: number;
  scoreLineType: number;
  deluxScore: string;
  dxStar: number;
  comboType: number;
  syncType: number;
  list: number;
  kaleidxHealth: string;
  playData: PlayData;
}

export interface PlayData {
  fast: number;
  late: number;
  notes: NotesData;
  rating: number;
  ratingKeep: number;
  ratingChange: number;
  maxCombo: string;
  maxSync: string;
  chara_id: number[];
  chara_star: number[];
  chara_level: number[];
  play_together_name: string[];
  play_together_diff: number[];
}

export interface NotesData {
  tap: number[];
  hold: number[];
  slide: number[];
  touch: number[];
  break: number[];
}


