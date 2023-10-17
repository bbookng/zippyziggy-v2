import Link from 'next/link';
import Router from 'next/router';
import { useState } from 'react';
import Lottie from 'react-lottie-player';
import styled from 'styled-components';
import { Logo, LogoContainer, StyledTitleContainer } from '@/components/Home/Home.style';

import lottieJson from '@/assets/lottieJson/background-pattern.json';

import { links } from '@/utils/links';

import Button from '@/components/Button/Button';
import Title from '@/components/Typography/Title';

const StyledAnchor = styled.a`
  color: ${({ color, theme: { colors } }) => colors[color]};
`;

const TitleContainer = () => {
  const [isPlaying1, setIsPlaying1] = useState(false);
  const [isPlaying2, setIsPlaying2] = useState(false);
  const [isPlaying3, setIsPlaying3] = useState(false);
  const [isPlaying4, setIsPlaying4] = useState(false);

  return (
    <StyledTitleContainer>
      <Logo>
        <div
          className="container t1"
          onMouseEnter={() => {
            setIsPlaying1(true);
          }}
          onMouseLeave={() => {
            setIsPlaying1(false);
          }}
        >
          <div className="guard" />
          <Lottie className="lottie" loop animationData={lottieJson} play={isPlaying1} />
        </div>
        <div
          className="container t2 cursor"
          onClick={() => {
            Router.push(links.prompts);
          }}
          onMouseEnter={() => {
            setIsPlaying2(true);
          }}
          onMouseLeave={() => {
            setIsPlaying2(true);
          }}
        >
          <div className="guard" />
          <Lottie className="lottie" loop animationData={lottieJson} play />
        </div>
        <div
          className="container t3 cursor"
          onClick={() => {
            Router.push(links.talks);
          }}
          onMouseEnter={() => {
            setIsPlaying3(true);
          }}
          onMouseLeave={() => {
            setIsPlaying3(false);
          }}
        >
          <div className="guard" />
          <Lottie className="lottie" loop animationData={lottieJson} play={isPlaying3} />
        </div>
      </Logo>

      <Logo>
        <div
          className="container t4 cursor"
          onClick={() => {
            Router.push(links.downloadLink);
          }}
          onMouseEnter={() => {
            setIsPlaying4(true);
          }}
          onMouseLeave={() => {
            setIsPlaying4(false);
          }}
        >
          <div className="guard" />
          <Lottie className="lottie" loop animationData={lottieJson} play />
        </div>
      </Logo>
      <LogoContainer>
        <Title className="title" color="whiteColor" textAlign="center">
          지피티를 알면 질문도 잘할 수 있다!
          <br />
          GPT 프롬프트 및 대화 공유 사이트 ZippyZiggy
        </Title>
        <StyledAnchor
          className="sub"
          color="blackColor90"
          target="_blank"
          rel="noopener noreferrer"
          href={links.noticeLink}
        >
          버전 1.3.9 release
        </StyledAnchor>
        <Link href={links.canny}>
          <Button buttonType="outline" margin="16px 0 16px 0">
            피드백을 공유해주세요
          </Button>
        </Link>
        {/* <Paragraph margin="16px 0 0 0" color="blackColor90" textAlign="center" sizeType="xm">
        누적 방문자 수 : {} &nbsp;&nbsp;|&nbsp;&nbsp; 오늘 이용자 수 :{' '}
        {}
      </Paragraph> */}
      </LogoContainer>
    </StyledTitleContainer>
  );
};

export default TitleContainer;
