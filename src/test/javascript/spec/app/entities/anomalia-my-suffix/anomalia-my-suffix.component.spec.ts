/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { AnomaliaMySuffixComponent } from '../../../../../../main/webapp/app/entities/anomalia-my-suffix/anomalia-my-suffix.component';
import { AnomaliaMySuffixService } from '../../../../../../main/webapp/app/entities/anomalia-my-suffix/anomalia-my-suffix.service';
import { AnomaliaMySuffix } from '../../../../../../main/webapp/app/entities/anomalia-my-suffix/anomalia-my-suffix.model';

describe('Component Tests', () => {

    describe('AnomaliaMySuffix Management Component', () => {
        let comp: AnomaliaMySuffixComponent;
        let fixture: ComponentFixture<AnomaliaMySuffixComponent>;
        let service: AnomaliaMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [AnomaliaMySuffixComponent],
                providers: [
                    AnomaliaMySuffixService
                ]
            })
            .overrideTemplate(AnomaliaMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnomaliaMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnomaliaMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AnomaliaMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.anomalias[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
