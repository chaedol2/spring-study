    let coinPrice=[]; //전역변수 선언
    let gstRatioAmount;
    let gmtRatioAmount;
    let mainRatioAmount;
    let isLvlUp;
    let gstMint;
    let gmtMint;
    let totalMintCostKrw;
    let floorPrice;
    let salesCostKrw;
    let totalProfitKrw;
    let profitKrw;

    //페이지 실행시 실행
    $(function (){
        fetchCoinPriceSol();//솔체인 시세불러오기
        InputChangeMain();//SOL 1 기준 gst, gmt 비율계산
        MintRatioCalculator();//민팅비율 계산
    });

    //솔라나체인 코인가격 불러오기
    function fetchCoinPriceSol() {

        $.ajax({
            type: 'GET',
            url: 'https://api.coingecko.com/api/v3/simple/price?ids=green-satoshi-token%2Csolana%2Cstepn&vs_currencies=krw%2Cusd',
            dataType: 'json',
            async: false,
            success: function (data) {
                let solUsd = data.solana.usd;
                let solKrw = data.solana.krw;
                let solGstUsd = data['green-satoshi-token'].usd;
                let solGstKrw = data['green-satoshi-token'].krw;
                let solGmtUsd = data.stepn.usd;
                let solGmtKrw = data.stepn.krw
                $('#mainUsd').text(solUsd + '$');
                $('#mainKrw').text(solKrw + '원');
                $('#gstUsd').text(solGstUsd + '$');
                $('#gstKrw').text(solGstKrw + '원');
                $('#gmtUsd').text(solGmtUsd + '$');
                $('#gmtKrw').text(solGmtKrw + '원');
                $('#chainMainTitle').text("SOL");
                coinPrice.splice(0, 5, solUsd,
                                       solKrw,
                                       solGstUsd,
                                       solGstKrw,
                                       solGmtUsd,
                                       solGmtKrw);
            },
            error: function () {
                alert("ajax error");
            }
        });
    }

    //바이낸스체인 코인가격 불러오기
    function fetchCoinPriceBsc() {
        $.ajax({
            url: 'https://api.coingecko.com/api/v3/simple/price?ids=green-satoshi-token-bsc%2Cbinancecoin%2Cstepn&vs_currencies=krw%2Cusd',
            type: 'GET',
            async: false,
            success: function (data) {
                let solUsd = data.binancecoin.usd;
                let solKrw = data.binancecoin.krw;
                let solGstUsd = data['green-satoshi-token-bsc'].usd;
                let solGstKrw = data['green-satoshi-token-bsc'].krw;
                let solGmtUsd = data.stepn.usd;
                let solGmtKrw = data.stepn.krw
                $('#mainUsd').text(solUsd + '$');
                $('#mainKrw').text(solKrw + '원');
                $('#gstUsd').text(solGstUsd + '$');
                $('#gstKrw').text(solGstKrw + '원');
                $('#gmtUsd').text(solGmtUsd + '$');
                $('#gmtKrw').text(solGmtKrw + '원');
                $('#chainMainTitle').text("BNB");
                coinPrice.splice(0, 5, solUsd,
                                       solKrw,
                                       solGstUsd,
                                       solGstKrw,
                                       solGmtUsd,
                                       solGmtKrw);
            },
            error: function () {
                alert("ajax error");
            }
        });
    }

    //input값 변경 이벤트 발생시 비율 계산
    function InputChangeMain() {
        let compareMain = parseFloat(document.getElementById("compareMain").value);
        gstRatioAmount = coinPrice[0] * compareMain / coinPrice[2];
        gmtRatioAmount = coinPrice[0] * compareMain / coinPrice[4];

        document.getElementById("compareGst").value = gstRatioAmount.toFixed(2);
        document.getElementById("compareGmt").value = gmtRatioAmount.toFixed(2);
    }

    function InputChangeGst() {
        gstRatioAmount = parseFloat(document.getElementById("compareGst").value);
        mainRatioAmount = coinPrice[2] * gstRatioAmount / coinPrice[0];
        gmtRatioAmount = coinPrice[2] * gstRatioAmount / coinPrice[4];

        document.getElementById("compareMain").value = mainRatioAmount.toFixed(2);
        document.getElementById("compareGmt").value = gmtRatioAmount.toFixed(2);
    }

    function InputChangeGmt() {
        gmtRatioAmount = parseFloat(document.getElementById("compareGmt").value);
        mainRatioAmount = coinPrice[4] * gmtRatioAmount / coinPrice[0];
        gstRatioAmount = coinPrice[4] * gmtRatioAmount / coinPrice[2];

        document.getElementById("compareMain").value = mainRatioAmount.toFixed(2);
        document.getElementById("compareGst").value = gstRatioAmount.toFixed(2);
    }

    // 체인 라디오버튼 클릭시 이벤트 발생
    $("input:radio[name=gstType]").click(function () {
        if ($("input[name=gstType]:checked").val() == "sol") {
            fetchCoinPriceSol()
            document.getElementById("compareMain").value = 1;
            MintRatioCalculator()
        } else if ($("input[name=gstType]:checked").val() == "bsc") {
            fetchCoinPriceBsc()
            document.getElementById("compareMain").value = 1;
            MintRatioCalculator()
        }
    });

    //gst&gmt 민팅 비율계산기
    function MintRatioCalculator(){
        isLvlUp = document.getElementById("isLvlUp").value;
        isCommonUncommon = document.getElementById("isCommonUncommon").value;
        let firstMintCount = parseInt(document.getElementById("firstMintCount").value);
        let secondMintCount = parseInt(document.getElementById("secondMintCount").value);

        if (isCommonUncommon == 'common'){
            //커먼&커먼 gst비용 결정
            if (firstMintCount == secondMintCount){
                switch(firstMintCount){
                    case 0: gstMint = 360; gmtMint = gstMint/9; break;
                    case 1: gstMint = 360; gmtMint = gstMint/9; break;
                    case 2: gstMint = 540; gmtMint = gstMint/9; break;
                    case 3: gstMint = 720; gmtMint = gstMint/9; break;
                    case 4: gstMint = 900; gmtMint = gstMint/9; break;
                    case 5: gstMint = 1000; gmtMint = gstMint/9; break;
                    case 6: gstMint = 1260; gmtMint = gstMint/9; break;
                }
            }else if(firstMintCount<=1 || secondMintCount<=1){
                gstMint = (Math.max(firstMintCount, secondMintCount)+3)*9*10;
                gmtMint = gstMint/9;
            }else{
                gstMint = ((firstMintCount+secondMintCount)+2)*9*10;
                gmtMint = gstMint/9;
            }
        }else if(isCommonUncommon == 'uncommon'){
            //언커먼&언커먼 gst비용 결정
            if (firstMintCount == secondMintCount){
                switch(firstMintCount){
                    case 0: gstMint = 1360; gmtMint = gstMint/(17/3); break;
                    case 1: gstMint = 1360; gmtMint = gstMint/(17/3); break;
                    case 2: gstMint = 2040; gmtMint = gstMint/(17/3); break;
                    case 3: gstMint = 2720; gmtMint = gstMint/(17/3); break;
                    case 4: gstMint = 3400; gmtMint = gstMint/(17/3); break;
                    case 5: gstMint = 4080; gmtMint = gstMint/(17/3); break;
                    case 6: gstMint = 4760; gmtMint = gstMint/(17/3); break;
                }
            }else if(firstMintCount<=1 || secondMintCount<=1){
                gstMint = (Math.max(firstMintCount, secondMintCount)+3)*(17/3)*60;
                gmtMint = gstMint/(17/3);
            }else{
                gstMint = ((firstMintCount+secondMintCount)+2)*(17/3)*60;
                gmtMint = gstMint/(17/3);
            }
        }

        //1회 민팅지출 gst&gmt
        document.getElementById('gstMint').innerText = gstMint.toFixed(0);
        document.getElementById('gmtMint').innerText = gmtMint.toFixed(0);

        //총 민팅 지출비용
        if(document.getElementById('isLvlUp').checked == true){
            document.getElementById('lvlUpGst').innerText = 20;
            document.getElementById('lvlUpGmt').innerText = 10;
            gstMint = gstMint + 20;
            gmtMint = gmtMint + 10;
        }else{
            document.getElementById('lvlUpGst').innerText = 0;
            document.getElementById('lvlUpGmt').innerText = 0;
        }
        document.getElementById('totalCostGst').innerText = gstMint.toFixed(0);
        document.getElementById('totalCostGmt').innerText = gmtMint.toFixed(0);

        //총 민팅 지출비용 KRW환산
        totalMintCostKrw = gstMint * coinPrice[3] + gmtMint * coinPrice[5];
        document.getElementById('totalMintCostKrw').innerText = totalMintCostKrw.toFixed(0);

        //신발 바닥가(SOL) & 판매수수료(KRW) & 최소 이익비용(KRW) & 총 손익비용(KRW)
        floorPrice = document.getElementById('floorPrice').value;
        salesCostKrw = floorPrice * 0.06 * coinPrice[1];
        document.getElementById('salesCostKrw').innerText = salesCostKrw.toFixed(0);

        totalProfitKrw = floorPrice * 0.94 * coinPrice[1];
        document.getElementById('totalProfitKrw').innerText = totalProfitKrw.toFixed(0);

        profitKrw = totalProfitKrw - totalMintCostKrw;
        document.getElementById('profitKrw').innerText = profitKrw.toFixed(0);
    }