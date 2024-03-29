import { NextPage } from 'next';
import Footer from '@/components/Footer/Footer';
// import { getDailyVisited, getTotalVisited } from '@/core/user/userAPI';
import GuideContainer from '@/components/Home/GuideContainer';
import { Container } from '@/components/Home/Home.style';
import ScriptContainer from '@/components/Home/ScriptContainer';
import TalksContainer from '@/components/Home/TalksContainer';
import TitleContainer from '@/components/Home/TitleContainer';

interface HomePageProps {
  title: string;
}

const Home: NextPage<HomePageProps> = () => {
  return (
    <Container>
      <TitleContainer />
      <GuideContainer />
      <ScriptContainer />
      <TalksContainer />
      <Footer />
    </Container>
  );
};

// 이게 먼저 실행되고 컴포넌트 함수가 실행될 것임
export async function getServerSideProps() {
  // Client side에서는 실행되지 않음
  return {
    props: {
      title: '',
    },
  }; // props키가 있는 객체를 반환
}
export default Home;
