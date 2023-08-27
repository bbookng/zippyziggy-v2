/* eslint-disable no-param-reassign */

import { findRegenerateButton } from '@pages/content/utils/extension/common/find-regenerate-button';
import throttle from '@pages/content/utils/@shared/throttle';
import { createShareButton } from '@pages/content/utils/extension/input-portals/create-share-button';
import t from '@src/chrome/i18n';
import { authApi } from '@pages/content/utils/apis/axios-instance';
import { ZIPPY_SITE_URL, ZP_PROMPT_TITLE_HOLDER_ID } from '@pages/constants';
import { SHARE_MOBILE_SVG } from '@pages/content/components/Icons';

const resetShareButton = (shareButton: HTMLElement) => {
  const $shareButton = shareButton;
  $shareButton.style.cursor = 'pointer';
  $shareButton.blur();
  const isMobile = document.body.clientWidth < 768;
  if (isMobile) {
    $shareButton.children[0].innerHTML = SHARE_MOBILE_SVG;
  } else {
    $shareButton.innerHTML = $shareButton.innerHTML.replace(
      new RegExp(t('shareButton_loading'), 'g'),
      t('shareButton_ready')
    );
  }
};

const updateShareButtonState = ($shareButton) => {
  const isMobile = document.body.clientWidth < 768;
  if (isMobile) {
    $shareButton.children[0].textContent = '...';
    ($shareButton.children[0] as HTMLElement).style.width = '1.2rem';
  } else {
    $shareButton.innerHTML = $shareButton.innerHTML.replace(
      new RegExp(t('shareButton_ready'), 'g'),
      t('shareButton_loading')
    );
  }
  $shareButton.style.cursor = 'initial';
};

const getUuidFromRegex = (text: string) => {
  const regex = /prompts\/(.*)/;
  const match = text.match(regex);
  return match && match[1];
};

const createMessage = (role, content) => ({
  role,
  content,
});

const getMessagesFromThread = ($threadContainer: HTMLElement) => {
  const messages = [];

  for (const node of $threadContainer.children) {
    const markdown = node.querySelector('.markdown') as HTMLElement;
    if ([...node.classList].includes('dark:bg-gray-800')) {
      const warning = node.querySelector('.text-orange-500') as HTMLElement;
      if (warning) {
        messages.push(createMessage('USER', warning.innerText.split('\n')[0]));
      } else {
        const text = node.querySelector('.whitespace-pre-wrap') as HTMLElement;
        messages.push(createMessage('USER', text.textContent));
      }
    } else if (markdown) {
      messages.push(createMessage('ASSISTANT', markdown.outerHTML));
    }
  }

  return messages;
};

const isChatGptPlusUser = () => {
  const $chatGptPlusElement = document.querySelector('.gold-new-button') as HTMLElement;
  const isNotChatGptPlus = $chatGptPlusElement && $chatGptPlusElement.innerText.includes('Upgrade');
  return !isNotChatGptPlus;
};

const getConversationData = async (model) => {
  const $threadContainer = document.getElementsByClassName(
    'flex flex-col text-sm dark:bg-gray-800'
  )[0] as HTMLElement;

  const isChatGptPlus = isChatGptPlusUser();
  let $firstConversation = $threadContainer.firstChild;
  if (isChatGptPlus) {
    model = ($threadContainer.firstChild as HTMLElement).innerText;
    $firstConversation = ($threadContainer.firstChild as HTMLElement).nextElementSibling;
  }

  const $selectedPromptTitle = document.querySelector(
    `#${ZP_PROMPT_TITLE_HOLDER_ID}`
  ) as HTMLElement;
  const firstConversationText = $firstConversation.textContent;
  const selectedPromptUuid = $selectedPromptTitle.dataset.promptUuid;

  const uuidFromRegex = getUuidFromRegex(firstConversationText);

  return {
    promptUuid: selectedPromptUuid || uuidFromRegex || null,
    title: document.title,
    model,
    messages: getMessagesFromThread($threadContainer),
  };
};

const handleShareButtonClick = async ($shareButton) => {
  let isRequesting = false; // 공유 요청 state 관리
  const model = 'Model: Default (GPT-3.5)'; // GPT 모델

  if (isRequesting) return;

  const $textarea = document.querySelector(`form textarea`);
  const $submitButton = $textarea?.nextElementSibling; // 제출 버튼

  if ($submitButton.children[0].nodeName === 'DIV') return; // 제출버튼 자식의 노드 네임이 DIV일 때는 GPT가 프롬프트 답변 생성중

  isRequesting = true;

  updateShareButtonState($shareButton);

  const conversationData = await getConversationData(model);
  if (!conversationData) return;

  try {
    if (window.confirm(t('confirmMessage_conversationsShare'))) {
      const {
        data: { talkId },
      } = await authApi.post('/talks', conversationData);

      window.open(`${ZIPPY_SITE_URL}/talks/${talkId}`, '_blank');
    }
  } catch (err) {
    window.alert(t('errorMessage_conversationsShare'));
  } finally {
    isRequesting = false;
    resetShareButton($shareButton);
  }
};

export const appendShareButton = async () => {
  chrome.storage.sync.get('accessToken', ({ accessToken }) => {
    if (!accessToken) return;

    const $shareButton = createShareButton();
    if (!$shareButton) return;

    if (findRegenerateButton()) {
      findRegenerateButton().insertAdjacentElement('beforebegin', $shareButton);
    }

    $shareButton.addEventListener(
      'click',
      throttle(() => handleShareButtonClick($shareButton), 2000)
    );
  });
};
