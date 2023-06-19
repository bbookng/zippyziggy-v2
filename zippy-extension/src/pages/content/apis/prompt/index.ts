import { api, authApi } from '@pages/content/utils/apis/axios-instance';
import withErrorHandling from '@pages/content/utils/apis/withErrorHandling';
import {
  ExtensionSearchResult,
  PromptDetailResponse,
  PromptDetailResult,
} from '@pages/content/apis/prompt/models';

const PROMPT_API_ENDPOINT = '/prompts';

export const searchPrompts = withErrorHandling(async (params): Promise<ExtensionSearchResult> => {
  const {
    data: { extensionSearchPromptList, totalPromptsCnt, totalPageCnt },
  } = await authApi.get<ExtensionSearchResult>(`${PROMPT_API_ENDPOINT}/extension`, { params });
  return { extensionSearchPromptList, totalPromptsCnt, totalPageCnt };
});

export const getPromptDetail = withErrorHandling(
  async (uuid: string): Promise<PromptDetailResult> => {
    const {
      data: {
        messageResponse: { example, prefix, suffix },
        title,
      },
    } = await api.get<PromptDetailResponse>(`${PROMPT_API_ENDPOINT}/${uuid}`);
    return { title, prefix, example, suffix, uuid };
  }
);

export const toggleLikePrompt = withErrorHandling(async (uuid: string) => {
  await authApi.post(`${PROMPT_API_ENDPOINT}/${uuid}/like`);
});

export const toggleBookmarkPrompt = withErrorHandling(async (uuid: string) => {
  await authApi.post(`${PROMPT_API_ENDPOINT}/${uuid}/bookmark`);
});
