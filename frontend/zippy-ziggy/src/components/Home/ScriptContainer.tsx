import { links } from '@/utils/links';
import Router from 'next/router';
import TypeIt from 'typeit-react';
import { setPageReset } from '@/core/prompt/promptSlice';
import { useAppDispatch } from '@/hooks/reduxHook';

const ScriptContainer = () => {
  const dispatch = useAppDispatch();

  const handlePromptsBtn = () => {
    dispatch(setPageReset());
    Router.push(links.prompts);
  };

  const handleDownloadBtn = () => {
    Router.push(links.downloadLink);
  };

  return (
    <div id="prompts" className="scriptContainer">
      <div className="scriptWrap">
        <h2>
          많은 사람이 사용할 수 있게,
          <br /> 템플릿 형식의 프롬프트로 만들어볼까요?
        </h2>
        <p className="script">
          GPT에게 좋은 답변을 끌어낸 질문을 좋은 프롬프트라고 해요!
          <br />
          다른 사람도 사용하기 좋게 템플릿 형식으로 만들어서 공유하세요!
          <br />
          지피지기에는 이렇게 공유된 다양한 프롬프트들이 있습니다.
        </p>
        <div className="bottomContainer">
          <div className="questionWrap" style={{ width: 'fit-content' }}>
            <p className="question">
              {`'소나기'를 주제로 삼행시를 쓴 예시야
소 : 소방차가 불난 집 불을 끈다.
나 : 나는 신나게 구경을 했다.
기 : 기절했다. 우리 집이었다.
`}
              <span>
                <TypeIt
                  options={{ loop: true }}
                  getBeforeInit={(instance) => {
                    instance
                      .type(`이제 '지피티'를 주제로 삼행시를 써줘`)
                      .pause(1000)
                      .delete()
                      .type(`'지피지기'를 주제로 사행시를 써줘`)
                      .pause(1000)
                      .delete()
                      .type(`'햄버거'로 삼행시를 써줘`)
                      .pause(1000)
                      .delete();
                    return instance;
                  }}
                />
              </span>
            </p>
          </div>
          <div className="buttonContainer">
            <button type="button" onClick={handlePromptsBtn}>
              프롬프트 종류 둘러보기
            </button>
            <button type="button" onClick={handleDownloadBtn}>
              프롬프트를 쉽게 쓰는 방법!
              <br /> <span className="s2downBtn">확장 다운로드</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ScriptContainer;
