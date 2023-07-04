import { useState } from 'react';

const GuideContainer = () => {
  const [guideNum, setGuideNum] = useState(0);

  return (
    <div id="guide" className="scriptContainer">
      <div className="scriptWrap">
        <h2>GPT, 어떤 질문이 효과적일까요?</h2>
      </div>
      <div className="buttonContainer">
        <button
          type="button"
          className={`${guideNum === 1 && 'activeBtn'}`}
          onClick={() => {
            setGuideNum(1);
          }}
        >
          단순 질문
        </button>
        <button
          type="button"
          className={`${guideNum === 2 && 'activeBtn'}`}
          onClick={() => {
            setGuideNum(2);
          }}
        >
          자세하게
        </button>
        <button
          type="button"
          className={`${guideNum === 3 && 'activeBtn'}`}
          onClick={() => {
            setGuideNum(3);
          }}
        >
          예시 포함
        </button>
      </div>
      <div className={`questionContainer ${guideNum === 0 && 'visible'}`}>
        <div className="questionWrap">버튼을 🖱클릭하여 알아보세요!</div>
      </div>
      <div className={`questionContainer ${guideNum === 1 && 'visible'}`}>
        <p className="questionMessage">{`🧐"단순질문은 국어시간에 배운 삼행시를 알려주네요"`}</p>
        <div className="questionWrap">
          <p className="emphasis">질문: </p>
          <p className="question">지피티를 주제로 삼행시를 지어줘</p>
        </div>
        <div className="answerWrap">
          <p className="emphasis">대답: </p>
          <p className="answer">
            {`시작합니다!
지피티 대단한     언어모델의 대명사     챗봇에게 물어봐     정답 알려줄 거야
세상 지식 다 모은     오픈AI 덕분에     우리 모두 쉽게     알아갈 수 있어
하지만 능력 한계     기억은 매우 짧고     언제나 기반지식     살피며 답변할게`}
          </p>
        </div>
      </div>
      <div className={`questionContainer ${guideNum === 2 && 'visible'}`}>
        <p className="questionMessage">{`😥"자세하게 질문을 하려 했지만 쉽지 않아요"`}</p>
        <div className="questionWrap">
          <p className="emphasis">질문: </p>
          <p className="question">
            삼행시를 지어줘. 삼행시는 내가 불러주는 단어의 글자를 한 글자 한 글자로 나누어 그 한
            글자로 시작하는 문장의 모음이야. 지피티를 주제로 삼행시를 지어줘
          </p>
        </div>
        <div className="answerWrap">
          <p className="emphasis">대답: </p>
          <p className="answer">
            {`지극히 새로운
프로그래밍 시대
희망찬 미래,
지피티`}
          </p>
        </div>
      </div>
      <div className={`questionContainer ${guideNum === 3 && 'visible'}`}>
        <p className="questionMessage">{`🤩"예시를 포함하니 삼행시가 잘 나오네요!"`}</p>
        <div className="questionWrap">
          <p className="emphasis">질문: </p>
          <p className="question">
            {`'소나기'를 주제로 삼행시를 쓴 예시야
소 : 소방차가 불난 집 불을 끈다.
나 : 나는 신나게 구경을 했다.
기 : 기절했다. 우리 집이었다.
이제 '지피티'를 주제로 삼행시를 써줘`}
          </p>
        </div>
        <div className="answerWrap">
          <p className="emphasis">대답: </p>
          <p className="answer">
            {`지 : 지식과 경험을 모두 갖춘,
피 : 피드백에 항상 귀 기울이는,
티 : 티가 나는 인공지능 ChatGPT!`}
          </p>
        </div>
      </div>
    </div>
  );
};

export default GuideContainer;
