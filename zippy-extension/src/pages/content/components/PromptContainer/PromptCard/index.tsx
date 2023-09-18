import React from 'react';
import {
  CHAT_GPT_URL,
  PROMPT_PLACEHOLDER,
  TARGET_LANGUAGE_PLACEHOLDER,
  ZIPPY_SITE_URL,
  ZP_PROMPT_TITLE_HOLDER_ID,
} from '@pages/constants';
import { formatAgo, formatDateTime, formatHumanReadableNumber } from '@src/utils';
import { category } from '@pages/content/components/PromptContainer';
import ActionButton from '@pages/content/components/PromptContainer/PromptCard/ActionButton';
import t from '@src/chrome/i18n';
import { Prompt } from '@pages/content/apis/prompt/models';
import ViewsCountIcon from '@pages/content/components/Icons/prompt-card/ViewsCountIcon';
import CommentsCountIcon from '@pages/content/components/Icons/prompt-card/CommentsCountIcon';
import LikesCountIcon from '@pages/content/components/Icons/prompt-card/LikesCountIcon';

interface PromptCardProps {
  name: string;
  prompt: Prompt;
  queryKeyItems: {
    page: number;
    limit: number;
    debouncedSearchTerm: string;
    selectedSort: string;
    selectedCategory: string;
  };
}

const classList = ['w-full', 'rounded-md'];
const PromptCard = ({ name, prompt, queryKeyItems }: PromptCardProps) => {
  const {
    promptUuid,
    hit,
    example,
    likeCnt,
    title,
    prefix,
    regDt,
    suffix,
    updDt,
    description,
    isLiked,
    talkCnt,
    commentCnt,
    writerResponse,
    isBookmarked,
    category: promptCategory,
  } = prompt;

  const handlePromptClick = () => {
    const message = {
      type: 'selectPrompt',
      data: {
        prompt: `${ZIPPY_SITE_URL}/prompts/${promptUuid}\n위의 링크는 무시하세요.\n\n${
          prefix || ''
        } ${PROMPT_PLACEHOLDER} ${suffix || ''}${TARGET_LANGUAGE_PLACEHOLDER}`.trim(),
      },
    };

    const $textarea = document.querySelector(`form textarea`) as HTMLTextAreaElement;
    $textarea.placeholder = `ex) ${example}`;
    $textarea.style.overflowY = 'visible';
    $textarea.style.height = 'fit-content';

    const $selectedPromptTitle = document.querySelector(
      `#${ZP_PROMPT_TITLE_HOLDER_ID}`
    ) as HTMLElement;
    $selectedPromptTitle.textContent = `📟 ${title}`;
    $selectedPromptTitle.dataset.promptUuid = promptUuid;

    window.postMessage(message, CHAT_GPT_URL);
    if (document.getElementById('ZP_cancelPromptButton')) {
      document.getElementById('ZP_cancelPromptButton').style.display = 'block';
      return;
    }
    const $cancelPromptButton = document.createElement('button');
    $cancelPromptButton.id = 'ZP_cancelPromptButton';
    $cancelPromptButton.textContent = 'X';
    $cancelPromptButton.style.display = 'block';
    $cancelPromptButton.addEventListener('click', () => {
      window.postMessage({ type: 'cancelPrompt' }, CHAT_GPT_URL);
      $selectedPromptTitle.textContent = null;
      $selectedPromptTitle.dataset.promptUuid = '';
      $textarea.placeholder = 'Send a message.';
      $textarea.style.height = 'fit-content';
      $cancelPromptButton.style.display = 'none';
    });
    $selectedPromptTitle.parentElement.appendChild($cancelPromptButton);
  };

  return (
    <li className={`ZP_prompt-container__prompt-item ${classList.join(' ')}`}>
      <button
        className="ZP_prompt-container__prompt-button"
        type="button"
        onClick={handlePromptClick}
      >
        <article className="ZP_prompt-container__prompt-article">
          <div className="ZP_prompt-container__actions-wrapper--hover">
            <ActionButton
              name={name}
              type="goDetail"
              promptUuid={promptUuid}
              queryKeyItems={queryKeyItems}
            />
            <ActionButton
              name={name}
              type="like"
              promptUuid={promptUuid}
              fill={isLiked}
              queryKeyItems={queryKeyItems}
            />
            <ActionButton
              name={name}
              type="bookmark"
              promptUuid={promptUuid}
              fill={isBookmarked}
              queryKeyItems={queryKeyItems}
            />
          </div>
          <div className="ZP_prompt-container__content-wrapper">
            <h3 className="ZP_prompt-container__title" title={title} translate="yes">
              {title}
            </h3>
            <p className="ZP_prompt-container__category caption">
              {`${t('filterCategory')} / ${
                category.find((item) => item.id === promptCategory)?.text ?? ''
              }`}
            </p>
            <p className="ZP_prompt-container__description" title={description} translate="yes">
              {description}
            </p>
            <div className="ZP_prompt-container__info-wrapper">
              <p
                className="ZP_prompt-container__date caption"
                title={`마지막 업데이트 ${formatAgo(updDt * 1000)}`}
              >
                {formatDateTime(regDt * 1000, chrome.i18n.getUILanguage())}
              </p>
              <span className="middot caption">&middot;</span>
              <p className="caption">{formatHumanReadableNumber(talkCnt)} Talk</p>
            </div>
          </div>
          <div className="ZP_prompt-container__profile-wrapper">
            <div className="ZP_prompt-container__profile caption">
              <span>
                <img src={writerResponse?.writerImg} alt={writerResponse?.writerNickname} />
              </span>
              <span translate="no">{writerResponse?.writerNickname}</span>
            </div>
            <div className="ZP_prompt-container__actions-wrapper">
              {/* 조회수 */}
              <div className="ZP_prompt-container__view caption">
                <ViewsCountIcon />
                <span>{formatHumanReadableNumber(hit)}</span>
              </div>
              {/* 댓글 */}
              <div className="ZP_prompt-container__comment">
                <CommentsCountIcon />
                <span>{formatHumanReadableNumber(commentCnt)}</span>
              </div>
              {/* 좋아요 */}
              <div className={`ZP_prompt-container__like caption ${isLiked ? 'like' : ''}`}>
                <LikesCountIcon />
                <span>{formatHumanReadableNumber(likeCnt)}</span>
              </div>
            </div>
          </div>
        </article>
      </button>
    </li>
  );
};

export default PromptCard;
