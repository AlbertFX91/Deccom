import { Component, OnInit, OnDestroy } from '@angular/core';
import { CVService } from '../cv/cv.service';
import { EventService } from '../event/event.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
// import { Chart } from 'chart.js';
import 'chartjs-plugin-annotation';

import * as $ from 'jquery';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
}) export class DashboardComponent implements OnInit, OnDestroy {

    chart: any[];
    chartType: string;
    CVs: any[];
    events: any[];
    chartDataAux: any[];
    chartOptions: any;
    chartAnnotations: any[];
    today: Date;
    last: Date;
    page: any;
    itemsPerPage: number;
    pageSettings: any;
    loaded: boolean;

    interval: string;

    constructor(
        public cvService: CVService,
        public eventService: EventService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.init();
    }

    ngOnInit() {
        this.init();
        this.loadAll();
    }

    ngOnDestroy() { }

    init() {
        this.chart = [];
        this.chartType = 'scatter';
        this.CVs = [];
        this.events = [];
        this.chartDataAux = [];
        this.chartOptions = {};
        this.chartAnnotations = [];
        this.today = new Date();
        this.last = new Date(this.today.getTime() - (7 * 24 * 60 * 60 * 1000));
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.loaded = false;
        this.interval = 'WEEK';
    }

    loadAll() {
        this.cvService.dates(this.last.toISOString(), this.pageSettings).subscribe(
            (data: any) => this.onSuccessCV(data.json(), data.headers),
            (error: Response) => this.onError(error)
        );
        this.chartOptions = {
            responsive: true,
            legend: {
                display: true
            },
            scales: {
                xAxes: [{
                    id: 'x-axis-0',
                    display: true,
                    type: 'time',
                    time: {
                        unit: this.getUnitByInterval(this.interval),
                        unitStepSize: 1,
                        min: this.dateToNumber(new Date(this.last)),
                        displayFormats: {
                            day: 'll'
                        }
                    }
                }],
                yAxes: [{
                    id: 'y-axis-0',
                    type: 'logarithmic',
                    display: true,
                    min: 0,
                    ticks: {
                        display: false,
                        beginAtZero: true
                    }
                }]
            },
            tooltips: {
                callbacks: {
                    title: function(tooltipItem, data) {
                        // Removing milliseconds from the xLabel
                        let s_date = tooltipItem[0].xLabel;
                        const i = s_date.lastIndexOf('.');
                        const j = s_date.lastIndexOf(' ');

                        // Constructing the new date string
                        s_date = s_date.substring(0, i) + s_date.substring(j, s_date.length);
                        const showDate = new Date(s_date).toUTCString();

                        return showDate.substring(0, showDate.length - 4);
                    },
                    label: function(tooltipItem, data) {
                        let label = '' + data.datasets[tooltipItem.datasetIndex].label || '';

                        if (label) {
                            label += ': ';
                        }

                        label += tooltipItem.yLabel;
                        return label;
                    }
                }
            },
            annotation: {
                events: ['mouseenter'],
                annotations: this.chartAnnotations,
            }
        };
        /*
        const canvas: any = document.getElementById('myChart');
        const ctx = canvas.getContext('2d');
        this.chart = new Chart(ctx, {
            type: this.chartType,
            data: {
                datasets: this.CVs
            },
            options: this.chartOptions
        });
        */
    }

    onSuccessCV(data: any, headers: any) {
        for (let i = 0; i < data.content.length; i++) {
            const dataToInsert: any[] = [];
            for (let j = 0; j < data.content[i]['controlVarEntries'].length; j++) {
                const date: Date = new Date(data.content[i]['controlVarEntries'][j]['creationMoment']);
                // date.setHours(date.getHours() + 2);
                dataToInsert.push({
                    x: this.dateToNumber(date),
                    y: data.content[i]['controlVarEntries'][j]['value']
                });
            }
            const color = this.getRandomColor();
            const dato: any = {
                data: dataToInsert,
                label: data.content[i]['name'],
                backgroundColor: color,
                borderColor: color,
                borderWidth: '2px',
                fill: false,
                showLine: true
            }
            this.CVs.push(dato);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
        this.eventService.dates(this.last.toISOString(), this.today.toISOString(), this.pageSettings).subscribe(
            (res: ResponseWrapper) => this.onSuccessEvent(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    onSuccessEvent(data: any, headers: any) {
        for (let i = 0; i < data.length; i++) {
            const startingDate: any = data[i]['startingDate'];
            const endingDate: any = data[i]['endingDate'];
            let type: string;
            let value, xMin, xMax: number;
            if (endingDate === null) {
                type = 'line';
                value = this.dateToNumber(new Date(startingDate))
            } else {
                type = 'box';
                xMin = this.dateToNumber(new Date(startingDate));
                if (this.dateToNumber(new Date(endingDate)) > this.dateToNumber(new Date(this.today))) {
                    xMax = this.dateToNumber(new Date(this.today));
                } else {
                    xMax = this.dateToNumber(new Date(endingDate));
                }
            }

            const borderColor = type === 'line' ? 'red' : 'lightgray';

            const chartAnnotation: any = {
                borderColor: borderColor,
                mode: 'vertical',
                type: type,
                value: value,
                xMin: xMin,
                xMax: xMax,
                scaleID: 'x-axis-0',
                xScaleID: 'x-axis-0',
                label: {
                    fontFamily: 'roboto',
                    enabled: true,
                    content: data[i]['name']
                },
                backgroundColor: 'rgba(0,151,167,0.4)',
                onMouseenter: function(e) {
                    const element = this;
                    if (element.options.type !== 'box') {
                        return;
                    }
                    const left = element._model.left;
                    const right = element._model.right;
                    const top = element._model.top;
                    const bottom = element._model.bottom;

                    // It is mandatory to embed the CSS because the CSS of the ts component does not apply to this element

                    const msg = $('<div>' + element.options.label.content + '</div>')
                        .addClass('tooltiptext')
                        .css({
                            'visibility': 'visible',
                            'width': '100px',
                            'background-color': 'rgb(0,0,0,0.75)',
                            'color': '#fff',
                            'text-align': 'center',
                            'border-radius': '6px',
                            'padding': '5px 0',
                            'z-index': '500',
                            'position': 'relative',
                            'font-family': 'roboto',
                            'font-size': '12px',
                            'font-weight': 'bold',
                            'left': right + 5,
                            'top': (top + bottom) / 2
                        });
                    msg.append('<style>.tooltiptext:after{\
                        content: "";\
                        position: absolute;\
                        top: 50%;\
                        right: 100%;\
                        margin-top: -5px;\
                        border-width: 5px;\
                        border-style: solid;\
                        border-color: transparent rgb(0,0,0,0.75) transparent transparent;\
                    }</style>');

                    $('#overlay').append(msg);
                },
                onMouseout: function(e) {
                    $('#overlay').text('');
                }
            }
            this.chartAnnotations.push(chartAnnotation);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
        this.loaded = true;
    }

    setInterval(name) {
        this.init();
        this.interval = name;
        this.last = this.getDictIntervals()[name](new Date());
        this.loadAll();
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    private dateToNumber(date: Date) {
        date.setHours(date.getHours() + 2);
        return date.getTime();
    }
    getDictIntervals() {
        return {
            'HOUR': (today) => new Date(today.setHours(today.getHours() - 1)),
            'DAY': (today) => new Date(new Date().setDate(today.getDate() - 1)),
            'WEEK': (today) => new Date(new Date().setDate(today.getDate() - 7)),
            'MONTH': (today) => new Date(today.setMonth(today.getMonth() - 1)),
            'YEAR': (today) => new Date(today.setFullYear(today.getFullYear() - 1)),
        };
    }

    getIntervals() {
        return Object.keys(this.getDictIntervals());
    }

    getUnitByInterval(interval: string) {
        const units = {
            'HOUR': 'hour',
            'DAY': 'hour',
            'WEEK': 'day',
            'MONTH': 'day',
            'YEAR': 'month',
        }
        return units[interval];
    }

    private getRandomColor() {
        return '#' + Math.floor(Math.random() * 16777215).toString(16);
    }
}
