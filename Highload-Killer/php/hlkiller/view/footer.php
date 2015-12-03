<script type="text/javascript">
    $(function() {

        $('.hl_btn').on('click', function() {
            $('.generete_block').hide()
            $('.high_load_block').show()
            $('.hl_btn').addClass('btn-success')
            $('.gen_btn').removeClass('btn-success')
        });
        $('.gen_btn').on('click', function() {
            $('.generete_block').show()
            $('.high_load_block').hide()
            $('.gen_btn').addClass('btn-success')
            $('.hl_btn').removeClass('btn-success')
        });

        var generate_count = 0;

        $('.gen_fish').on('click', function() {


            $.get('index.php?do=generate_fish&users_by_step=' + $('.users_by_step').val(), function(data) {

                $('#result_div').prepend('<a onclick="$(this).next().toggle();">Generate Request #' + generate_count + '</a><div class="box_shadow2 p10" style="display:none;">' + data + '</div> - <b class="green">Ok!</b><br/>');
                generate_count++;
                if (generate_count < parseInt($('.steps_count').val()) && generate_count < 1000000)
                    $('.gen_fish').click();

            });
        });

        $('.gen_stop').on('click', function() {
            generate_count = 1000001;
        });

        $('.clear_db').on('click', function() {

            $.get('index.php?do=clear_db&action=1', function(data) {

                $('#result_div').html(data);

            });
        });

        $('.clear_db2').on('click', function() {

            $.get('index.php?do=clear_db&action=2', function(data) {

                $('#result_div').html(data);

            });
        });


        var attack_count = 0;

        var attack_count2 = 0;

        $('.stop_attack').on('click', function() {
            attack_count = 1000001;
            attack_count2 = 1000001;
			
			mix_attack_count = 1000001;
			mix_attack_count2 = 1000001;
        });



        $('.max_select_attack').on('click', function() {

            attack_count++;
            var this_count = attack_count;
            $.get('index.php?do=make_select_attack&action=1&times=' + parseInt($('.query_php_push_count').val()), function(data) {
                $('#result_div').prepend('<a onclick="$(this).next().toggle();">SELECT Request [1] #' + this_count + '</a><div class="box_shadow2 p10" style="display:none;">' + data + '</div> - <b class="green">Ok!</b><br/>');
            });

            if (attack_count < parseInt($('.query_ajax_push_count').val()) && attack_count < 1000000) {
                setTimeout(function() {

                    $('.max_select_attack').click();

                }, Math.floor(1000 / parseInt($('.query_ajax_push_count').val())));
            }
            ;

        });

        $('.max_select_attack2').on('click', function() {
            attack_count2++;
            var this_count = attack_count2;
            $.get('index.php?do=make_select_attack&action=2&times=' + parseInt($('.query_php_push_count').val()), function(data) {
                $('#result_div').prepend('<a onclick="$(this).next().toggle();">SELECT Request [2] #' + this_count + '</a><div class="box_shadow2 p10" style="display:none;">' + data + '</div> - <b class="green">Ok!</b><br/>');
            });

            if (attack_count2 < parseInt($('.query_ajax_push_count').val()) && attack_count2 < 1000000) {
                setTimeout(function() {

                    $('.max_select_attack2').click();

                }, Math.floor(1000 / parseInt($('.query_ajax_push_count').val())));
            }
            ;
        });

        $('.max_select_attack').on('click', function() {

            attack_count++;
            var this_count = attack_count;
            $.get('index.php?do=make_select_attack&action=1&times=' + parseInt($('.query_php_push_count').val()), function(data) {
                $('#result_div').prepend('<a onclick="$(this).next().toggle();">SELECT Request [1] #' + this_count + '</a><div class="box_shadow2 p10" style="display:none;">' + data + '</div> - <b class="green">Ok!</b><br/>');
            });

            if (attack_count < parseInt($('.query_ajax_push_count').val()) && attack_count < 1000000) {
                setTimeout(function() {
                    $('.max_select_attack').click();
                }, Math.floor(1000 / parseInt($('.query_ajax_push_count').val())));
            }
            ;

        });

        $('.mixed_select_attack').on('click', function() {

            mix_attack_count++;
            var $that = $(this);
            var pushCount = parseInt($('.query_php_push_count').val());
            var this_count = mix_attack_count;

            $.get('index.php?do=make_random_query&times=' + pushCount, function(data) {
                $('#result_div').prepend('<a onclick="$(this).next().toggle();">Mixed Request [1] #' + this_count + '</a><div class="box_shadow2 p10" style="display:none;">' + data + '</div> - <b class="green">Ok!</b><br/>');
            });
            $.get('index.php?do=make_select_attack&action=1&times=' + pushCount, function(data) {
                $('#result_div').prepend('<a onclick="$(this).next().toggle();">SELECT Request [1] #' + this_count + '</a><div class="box_shadow2 p10" style="display:none;">' + data + '</div> - <b class="green">Ok!</b><br/>');
            });

            if (mix_attack_count < pushCount && mix_attack_count < 1000000) {
                setTimeout(function() {
                    $that.click();
                }, Math.floor(1000 / pushCount));
            };

        });

        $('.mixed_select_attack2').on('click', function() {

            mix_attack_count2++;
            var $that = $(this);
            var pushCount = parseInt($('.query_php_push_count').val());
            var this_count = mix_attack_count2;

            $.get('index.php?do=make_random_query&times=' + pushCount, function(data) {
                $('#result_div').prepend('<a onclick="$(this).next().toggle();">Mixed Request [2] #' + this_count + '</a><div class="box_shadow2 p10" style="display:none;">' + data + '</div> - <b class="green">Ok!</b><br/>');
            });
            $.get('index.php?do=make_select_attack&action=1&times=' + pushCount, function(data) {
                $('#result_div').prepend('<a onclick="$(this).next().toggle();">SELECT Request [2] #' + this_count + '</a><div class="box_shadow2 p10" style="display:none;">' + data + '</div> - <b class="green">Ok!</b><br/>');
            });

            if (mix_attack_count2 < pushCount && mix_attack_count2 < 1000000) {
                setTimeout(function() {
                    $that.click();
                }, Math.floor(1000 / pushCount));
            };

        });


        var free_request = 0;
        $('.push_to_final').on('click', function() {

            this_count = free_request;


            $.get('index.php?do=XXXXXXXXX', function(data) {
                $('#result_div').prepend('<a onclick="$(this).next().toggle();">FREE Request #' + this_count + '</a><div class="box_shadow2 p10" style="display:none;">' + data + '</div> - <b class="green">Ok!</b><br/>');
            });

        });


    });
</script>