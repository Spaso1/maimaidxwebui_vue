import type { PlaylogRecord } from '../types/playlog';
import type { MusicData, Score } from '../types/music';

const API_BASE_URL = '';

async function request<T>(endpoint: string, options?: RequestInit): Promise<T> {
  const headers: HeadersInit = {
    'Content-Type': 'application/json',
    'X-Session': 'your-session-token',
    ...options?.headers,
  };

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    throw new Error(`API error: ${response.status} ${response.statusText}`);
  }

  return response.json();
}

export const api = {
  get: <T>(endpoint: string, options?: RequestInit) => request<T>(endpoint, { ...options, method: 'GET' }),
  post: <T>(endpoint: string, data?: any, options?: RequestInit) =>
      request<T>(endpoint, {
        ...options,
        method: 'POST',
        body: JSON.stringify(data),
      }),
  getPlaylogDetail: (recordId: string) => request<PlaylogRecord>(`/api/playlogDetail?recordId=${recordId}`),
  getMusic: (filters?: { genre?: string; word?: string; level?: string; version?: string; diff?: string ;mode?: string }) => {
    const params = new URLSearchParams();
    if (filters?.genre) params.append('genre', filters.genre);
    if (filters?.word) params.append('word', filters.word);
    if (filters?.level) params.append('level', filters.level);
    if (filters?.version) params.append('version', filters.version);
    if (filters?.diff) params.append('diff', filters.diff);
    if (filters?.mode) params.append('diff', filters.mode);

    return request<MusicData>(`/api/music?${params.toString()}`);
  },
  getScore: (chartId: string | number) => request<Score>(`/api/score?chart_id=${chartId}`),
};
