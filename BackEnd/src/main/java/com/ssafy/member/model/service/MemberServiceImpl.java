package com.ssafy.member.model.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.board.model.mapper.BoardMemoMapper;
import com.ssafy.file.model.mapper.FileMapper;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

import com.ssafy.member.model.mapper.MemberMapper;

@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {

	private static final int KEY_STRETCHING_COUNT = 500; // 키 스트레칭 횟수
	
	private final MemberMapper memberMapper;
	private final FileMapper fileMapper;
	private final BoardMemoMapper boardMemoMapper;
	private final BoardMapper boardMapper;
	
//	@Override
//	public MemberDto login(Map<String, String> map) throws Exception {
//		String userid = map.get("userid");
//		String userpass = map.get("userpass");
//		String salt = memberMapper.findSaltByUserId(userid);
//		if (salt == null) { // 솔트가 존재하지 않는 경우(존재하지 않는 아이디)
//			return null;
//		}
//
//		String encryptedPass = keyStretching(userpass + salt); // 사용자입력비밀번호+유저의솔트값 해싱
//		map.put("userpass", encryptedPass);
//		System.out.println(map);
//		return memberMapper.login(map);
//	}
//	
//	@Override
//	public MemberDto login(MemberDto memberDto) throws Exception {
//		return memberMapper.login(memberDto);
//	}
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		String userid = memberDto.getUser_id();
		String userpass = memberDto.getUser_pass();
		String salt = memberMapper.findSaltByUserId(userid);
		if (salt == null) { // 솔트가 존재하지 않는 경우(존재하지 않는 아이디)
			return null;
		}

		String encryptedPass = keyStretching(userpass + salt); // 사용자입력비밀번호+유저의솔트값 해싱
		memberDto.setUser_pass(encryptedPass);
		return memberMapper.login(memberDto);
	}
	
	@Override
	public MemberDto userInfo(String userId) throws Exception {
		return memberMapper.userInfo(userId);
	}

	@Override
	public int join(MemberDto dto) throws Exception {
		// 솔트 생성
		String currentTime = Long.toString(System.currentTimeMillis());
		String salt = encodeSha256(currentTime);
		dto.setSalt(salt); // 솔트값 DTO에 저장
		
		// 비밀번호 해싱
		String encryptedPass = keyStretching(dto.getUser_pass() + salt); // 비밀번호+솔트를 해싱
		dto.setUser_pass(encryptedPass); // 비밀번호 암호화 처리한 값으로 DTO에 저장
		System.out.println(dto);
		return memberMapper.join(dto);
	}

	@Override
	public int modify(MemberDto dto) throws Exception {
		return memberMapper.modify(dto);
	}

	@Override
	public int modify_pw(String userId, String newPw) throws Exception {
		String salt = memberMapper.findSaltByUserId(userId);
		if (salt == null) { // 솔트가 존재하지 않는 경우(존재하지 않는 아이디)
			return 0;
		}
		String encryptedPass = keyStretching(newPw + salt); // 비밀번호+솔트를 해싱
		
		Map<String, String> map = new HashMap<>();
		map.put("user_id", userId);
		map.put("user_pass", encryptedPass);
		return memberMapper.modify_pw(map);
	}
	
	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}

	@Override
	@Transactional
	public int delete(String userId) throws Exception {
		fileMapper.deleteFile2(userId);
		boardMemoMapper.deleteMemoAll2(userId);
		boardMapper.deleteArticle2(userId);
		
		return memberMapper.delete(userId);
	}

	// 해싱 알고리즘, SHA-256 사용
	public String encodeSha256(String plainText) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(plainText.getBytes(StandardCharsets.UTF_8));
		return DatatypeConverter.printBase64Binary(md.digest());
	}
	
	// 키스트레칭
	public String keyStretching(String plainText) throws NoSuchAlgorithmException {
		String cipherText = plainText;
		for (int i = 0; i < KEY_STRETCHING_COUNT; i++) {
			cipherText = encodeSha256(cipherText); // SHA-256으로 해싱
		}
		return cipherText;
	}

	@Override
	public void saveRefreshToken(String user_id, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberMapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", userId);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}

}
