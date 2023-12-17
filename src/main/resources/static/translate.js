// translate.js
$(document).ready(function() {
    function translate() {
        var sentenceInput = $('#sentence');
        var translationResultDiv = $('#translationResult');

        // 입력 상자의 값으로 API 호출
        $.get('/translate/result', { sentence: sentenceInput.val() })
            .done(function (data) {
                // 번역 결과를 div에 표시
                translationResultDiv.text('Translated Sentence: ' + data);
            })
            .fail(function () {
                console.error('Error during translation');
                alert('번역 중 오류가 발생했습니다.');
            });
    }

    // 폼 제출을 막고 대신 translate 함수 호출
    $('form').submit(function() {
        translate();
        return false;
    });
});
