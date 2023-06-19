import {Category} from "@pages/content/types";

export interface Writer {
  writerUuid: string;
  writerImg: string;
  writerNickname: string;
}

export interface MessageResponse {
  prefix: string;
  example: string;
  suffix: string;
}

export interface PromptDetailResponse {
  writer: Writer;
  originer: any;
  title: string;
  description: string;
  thumbnail: string;
  likeCnt: number;
  isLiked: boolean;
  isBookmarked: boolean;
  isForked: boolean;
  category: string;
  regDt: number;
  updDt: number;
  hit: number;
  originPromptUuid: any;
  originPromptTitle: any;
  messageResponse: MessageResponse;
}

export interface PromptDetailResult {
  title: string;
  prefix: string | null;
  example: string;
  suffix: string | null;
  uuid: string;
}

export interface WriterResponse {
  writerImg: string;
  writerNickname: string;
  writerUuid: string;
}

export interface Prompt {
  category: Category['value'];
  commentCnt: number;
  description: string;
  example: string;
  hit: number;
  isBookmarked: boolean;
  isLiked: boolean;
  likeCnt: number;
  originalPromptUuid: string | null;
  prefix: string | null;
  promptUuid: string;
  regDt: number;
  suffix: string | null;
  talkCnt: number;
  thumbnail: string;
  title: string;
  updDt: number;

  writerResponse: WriterResponse;
}

export interface ExtensionSearchResult {
  extensionSearchPromptList: Array<Prompt>;
  totalPageCnt: number;
  totalPromptsCnt: number;
}