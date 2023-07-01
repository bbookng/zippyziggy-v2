import React from 'react';
import Image from 'next/image';
import { FaComment, FaHeart } from 'react-icons/fa';
import Router from 'next/router';
import Link from 'next/link';
import { ColorBox } from './TalkCardStyle';
import ProfileImage from '../Image/ProfileImage';

interface PropsType {
  talk: any;
  url: string;
}

export default function TalkCard({ talk, url }: PropsType) {
  const handleTalksClick = (writerUuid) => {
    return () => {
      Router.push(`${writerUuid}`);
    };
  };
  return (
    <div onClick={handleTalksClick(url)}>
      <ColorBox>
        <div className="title">{talk.title}</div>
        <div className="footBox">
          <div className="userBox">
            <Link href={`/profile/${talk.writer.writerUuid}`}>
              <ProfileImage
                src={talk.writer.writerImg}
                alt="프로필 사진"
                size={20}
                className="img"
              />
            </Link>
            <div className="nickname">{talk.writer.writerNickname}</div>
          </div>
          <div className="infoBox">
            <div className="heartBox">
              <FaHeart className="icon" />
              {talk.likeCnt}
            </div>
            <div className="commentBox">
              <FaComment className="icon" />
              {talk.commentCnt}
            </div>
          </div>
        </div>
      </ColorBox>
    </div>
  );
}
