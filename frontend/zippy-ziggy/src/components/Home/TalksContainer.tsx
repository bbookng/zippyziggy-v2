import { links } from '@/utils/links';
import Router from 'next/router';

const TalksContainer = () => {
  const handleTalksBtn = () => {
    Router.push(links.talks);
  };

  return (
    <div id="talks" className="scriptContainer">
      <h2>Talks</h2>
      <p>
        다른 사람들은 GPT에게 어떻게 질문할까요?
        <br />
        재밌거나 좋은 대답을 공유해보세요!
      </p>
      <div className="buttonContainer">
        <button type="button" onClick={handleTalksBtn}>
          대화 보러 가기
        </button>
      </div>
    </div>
  );
};

export default TalksContainer;
