import { useEffect, useState } from 'react';
import { useAppSelector, useAppDispatch } from '@/hooks/reduxHook';
import Title from '@/components/Typography/Title';
import {
  deleteNoticeItemAPI,
  deleteNoticeListAPI,
  getNoticeListAPI,
  getNoticeUnreadCountAPI,
  putNoticeItemAPI,
  putNoticeListAPI,
} from '@/core/notice/noticeAPI';
import NoticesList from '@/components/Notice/NoticeList';
// userSlice에서 유저 정보 변경
import { setNoticeCount } from '@/core/user/userSlice';
import { Container, Wrap } from '@/components/Notification/Notification.style';

function Index() {
  // let eventSource: EventSourcePolyfill | undefined;
  const token = localStorage.getItem('accessToken');
  const userState = useAppSelector((state) => state.user); // 유저정보
  const [noticeList, setNoticeList] = useState([]);
  const [noticeListSize, setNoticeListSize] = useState(0);
  const [countNoticeList, setCountNoticeList] = useState(10);
  const dispatch = useAppDispatch();

  // 알림 리스트 전체 받아오기
  const getNoticeList = async (size = countNoticeList) => {
    const result = await getNoticeListAPI({ page: 0, size });
    if (result.result === 'SUCCESS') {
      setNoticeList(result.data);
    }
  };

  // 알림 리스트 사이즈 받아오기
  const getNoticeListSize = async () => {
    const result = await getNoticeUnreadCountAPI();
    if (result.result === 'SUCCESS') {
      setNoticeListSize(result.data);
      dispatch(setNoticeCount(result.data));
    }
  };

  useEffect(() => {
    getNoticeList();
    getNoticeListSize();
  }, [token]);

  // 모두 삭제 클릭
  const handleNoticeDelete = async () => {
    const result = await deleteNoticeListAPI();
    if (result.result === 'SUCCESS') {
      getNoticeList();
      getNoticeListSize();
    }
  };

  // 모두 조회 클릭
  const handleNoticeRead = async () => {
    const result = await putNoticeListAPI();
    if (result.result === 'SUCCESS') {
      getNoticeList();
      getNoticeListSize();
    }
  };

  // 아이템 조회 클릭
  const handleItemReadClick = async (alarmId: number) => {
    const result = await putNoticeItemAPI({ alarmId });
    if (result.result === 'SUCCESS') {
      getNoticeList();
      getNoticeListSize();
    }
  };

  // 아이템 삭제 클릭
  const handleItemDeleteClick = async (alarmId: number) => {
    const result = await deleteNoticeItemAPI({ alarmId });
    if (result.result === 'SUCCESS') {
      getNoticeList();
      getNoticeListSize();
    }
  };

  // 더보기 버튼 클릭
  const handleMoreBtn = async () => {
    await getNoticeList(countNoticeList + 10);
    await getNoticeListSize();
    await setCountNoticeList(countNoticeList + 10);
  };

  return (
    <Container>
      <Wrap>
        <Title textAlign="left">알림{`(${noticeListSize})`}</Title>
        <div className="allButtonsContainer">
          <button type="button" onClick={handleNoticeDelete}>
            전체 삭제
          </button>
          <button type="button" onClick={handleNoticeRead}>
            전체 읽음
          </button>
        </div>
        <NoticesList
          noticeList={noticeList}
          handleItemDeleteClick={handleItemDeleteClick}
          handleItemReadClick={handleItemReadClick}
        />
        <button className="moreBtn" type="button" onClick={handleMoreBtn}>
          더보기
        </button>
      </Wrap>
    </Container>
  );
}

export default Index;
