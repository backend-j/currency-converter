const main = {
    init: function () {
        const _this = this;
        _this.getCurrency();
        $("#remittance").number(true, 2);

        $('#receivingCountry').on('change', function () {
            _this.getCurrency();
        });

        $('#convert').on('click', function () {
            _this.convert();
        });

        $('.numbersOnly').keyup(function () {
            this.value = this.value.replace(/[^0-9\.]/g, '');
        });
    },

    getCurrency: function () {
        const data = {
            receivingCountry: $('#receivingCountry option:selected').val(),
        };
        $.ajax({
            url: "/currency",
            type: 'GET',
            data: data,
            contentType: "application/json; charset=utf-8"
        }).done(function (response) {
            const exchangeRate = response.exchangeRate;
            const timestamp = `(${response.timestamp} 조회 기준)`;
            const output = ` ${formatNumber(exchangeRate)} ${response.receivingCountry}/${response.source}`;

            $('#exchangeRate').val(exchangeRate);
            $('#amountReceived').text('');
            $('#exchangeRateDiv').text(output);
            $('#timestamp').text(timestamp);
        }).fail(function (error) {
            console.log(error)
        });
    },

    convert: function () {
        const data = {
            receivingCountry: $('#receivingCountry option:selected').val(),
            exchangeRate: $('input[name=exchangeRate]').val(),
            remittance: $('input[name=remittance]').val()
        };
        $.ajax({
            url: '/convert',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8"
        }).done(function (response) {
            const amount = formatNumber(response.amountReceived);
            $('#remittance-errors').text('');
            $('#amountReceived').text(`수취 금액은 ${amount} ${data.receivingCountry} 입니다.`);
        }).fail(function (error) {
            markingErrorField(error);
        })
    }
}

const markingErrorField = function (response) {
    const errorFields = response.responseJSON;
    const error = errorFields[0];
    const $field = $('#' + error['field']);
    if ($field && $field.length > 0) {
        $('#amountReceived').text('');
        $('#remittance-errors').text(error.defaultMessage);
    }
};

const formatNumber = function (data) {
    return $.number(data, 2);
}


main.init();
