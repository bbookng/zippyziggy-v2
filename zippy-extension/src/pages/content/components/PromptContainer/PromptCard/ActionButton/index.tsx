import React, { MouseEvent } from 'react';
import { ZIPPY_SITE_URL } from '@pages/constants';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { toggleBookmarkPrompt, toggleLikePrompt } from '@pages/content/apis/prompt';
import { getAccessToken } from '@pages/content/utils/apis/interceptors';
import t from '@src/chrome/i18n';
import BookmarkFillIcon from '@pages/content/components/Icons/prompt-card/BookmarkFillIcon';
import BookmarkOutlineIcon from '@pages/content/components/Icons/prompt-card/BookmarkOutlineIcon';
import LikeFillIcon from '@pages/content/components/Icons/prompt-card/LikeFillIcon';
import LikeOutlineIcon from '@pages/content/components/Icons/prompt-card/LikeOutlineIcon';
import LinkChainIcon from '@pages/content/components/Icons/prompt-card/LinkChainIcon';

interface ActionButtonProps {
  name: string;
  type: 'like' | 'bookmark' | 'goDetail';
  promptUuid: string;
  fill?: boolean;
  queryKeyItems: {
    page: number;
    limit: number;
    debouncedSearchTerm: string;
    selectedSort: string;
    selectedCategory: string;
  };
}

const ActionButton = ({ name, type, promptUuid, fill, queryKeyItems }: ActionButtonProps) => {
  const { limit, selectedSort, selectedCategory, page, debouncedSearchTerm } = queryKeyItems;
  const queryClient = useQueryClient();

  const updateLike = async (promptUuid: string, fill: boolean) => {
    // fill 이 true면 좋아요 -> 좋아요 취소 동작
    // false면 좋아요 동작
    const queryKey =
      name === 'searchCard'
        ? ['search', page, limit, debouncedSearchTerm, selectedSort, selectedCategory]
        : ['bookmark', page, limit, selectedSort];
    const cachedQueryData = queryClient.getQueryData(queryKey) as any;
    const previousData =
      name === 'searchCard'
        ? cachedQueryData.extensionSearchPromptList
        : cachedQueryData.promptCardResponseList;

    if (previousData) {
      queryClient.setQueryData(queryKey, (oldData) => {
        const data = oldData as any;
        const previousPromptList =
          name === 'searchCard' ? data.extensionSearchPromptList : data.promptCardResponseList;
        const newExtensionSearchPromptList = [...previousPromptList].map((prompt) => {
          if (prompt.promptUuid === promptUuid) {
            return {
              ...prompt,
              isLiked: !prompt.isLiked,
              likeCnt: fill ? prompt.likeCnt - 1 : prompt.likeCnt + 1,
            };
          }
          return prompt;
        });
        return name === 'searchCard'
          ? { ...data, extensionSearchPromptList: newExtensionSearchPromptList }
          : { ...data, promptCardResponseList: newExtensionSearchPromptList };
      });
    }
  };

  const updateBookmark = (promptUuid: string) => {
    const queryKey =
      name === 'searchCard'
        ? ['search', page, limit, debouncedSearchTerm, selectedSort, selectedCategory]
        : ['bookmark', page, limit, selectedSort];
    const cachedQueryData = queryClient.getQueryData(queryKey) as any;
    const previousData =
      name === 'searchCard'
        ? cachedQueryData.extensionSearchPromptList
        : cachedQueryData.promptCardResponseList;

    if (previousData) {
      queryClient.setQueryData(queryKey, (oldData) => {
        const data = oldData as any;
        const previousPromptList =
          name === 'searchCard' ? data.extensionSearchPromptList : data.promptCardResponseList;

        let newExtensionSearchPromptList;

        if (name === 'searchCard') {
          newExtensionSearchPromptList = [...previousPromptList].map((prompt) => {
            if (prompt.promptUuid === promptUuid) {
              return {
                ...prompt,
                isBookmarked: !prompt.isBookmarked,
              };
            }
            return prompt;
          });
        } else {
          newExtensionSearchPromptList = [...previousPromptList].map((prompt) => {
            if (prompt.promptUuid === promptUuid) {
              return {
                ...prompt,
                isBookmarked: !prompt.isBookmarked,
              };
            }
            return prompt;
          });
        }
        return name === 'searchCard'
          ? { ...data, extensionSearchPromptList: newExtensionSearchPromptList }
          : { ...data, promptCardResponseList: newExtensionSearchPromptList };
      });
    }
  };

  const { mutate: toggleLike } = useMutation(['toggleLike'], () => toggleLikePrompt(promptUuid), {
    onMutate: () => updateLike(promptUuid, fill),
  });

  const { mutate: toggleBookmark } = useMutation(
    ['toggleLike'],
    () => toggleBookmarkPrompt(promptUuid),
    {
      onMutate: () => updateBookmark(promptUuid),
    }
  );

  const handleLinkClick = (e: MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    window.open(`${ZIPPY_SITE_URL}/prompts/${promptUuid}`, '_blank');
  };

  const handleBookmarkClick = async (e: MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    const accessToken = await getAccessToken();
    if (!accessToken) {
      alert(t('errorMessage_noAuthentication'));
      return;
    }
    toggleBookmark();
  };

  const handleLikeClick = async (e: MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    const accessToken = await getAccessToken();
    if (!accessToken) {
      alert(t('errorMessage_noAuthentication'));
      return;
    }
    toggleLike();
  };

  switch (type) {
    case 'bookmark':
      return (
        <button
          className={`ZP_action-button bookmark ${fill ? 'fill' : ''}`}
          type="button"
          onClick={handleBookmarkClick}
        >
          {fill && <BookmarkFillIcon />}
          {fill || <BookmarkOutlineIcon />}
        </button>
      );
    case 'like':
      return (
        <button
          type="button"
          className={`ZP_action-button like ${fill ? 'fill' : ''}`}
          onClick={handleLikeClick}
        >
          {fill && <LikeFillIcon />}
          {fill || <LikeOutlineIcon />}
        </button>
      );
    case 'goDetail':
    default:
      return (
        <button type="button" className="ZP_action-button" onClick={handleLinkClick}>
          <LinkChainIcon />
        </button>
      );
  }
};

export default ActionButton;
